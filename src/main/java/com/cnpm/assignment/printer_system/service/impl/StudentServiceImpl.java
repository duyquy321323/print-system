package com.cnpm.assignment.printer_system.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cnpm.assignment.printer_system.entity.Bill;
import com.cnpm.assignment.printer_system.entity.BillPrintPackage;
import com.cnpm.assignment.printer_system.entity.Content;
import com.cnpm.assignment.printer_system.entity.Document;
import com.cnpm.assignment.printer_system.entity.PageStudent;
import com.cnpm.assignment.printer_system.entity.PrintPackage;
import com.cnpm.assignment.printer_system.entity.Printer;
import com.cnpm.assignment.printer_system.entity.PrinterDocument;
import com.cnpm.assignment.printer_system.entity.QAndA;
import com.cnpm.assignment.printer_system.entity.Student;
import com.cnpm.assignment.printer_system.entity.User;
import com.cnpm.assignment.printer_system.entity.id.BillPrintPackageId;
import com.cnpm.assignment.printer_system.entity.id.ContentId;
import com.cnpm.assignment.printer_system.enumeration.BillStatus;
import com.cnpm.assignment.printer_system.enumeration.ContentStatus;
import com.cnpm.assignment.printer_system.enumeration.PrinterStatusSPSO;
import com.cnpm.assignment.printer_system.exception.custom.CNPMNotFoundException;
import com.cnpm.assignment.printer_system.repository.BillPrintPackageRepository;
import com.cnpm.assignment.printer_system.repository.BillRepository;
import com.cnpm.assignment.printer_system.repository.DocumentRepository;
import com.cnpm.assignment.printer_system.repository.PageStudentRepository;
import com.cnpm.assignment.printer_system.repository.PrintPackageRepository;
import com.cnpm.assignment.printer_system.repository.PrinterDocumentRepository;
import com.cnpm.assignment.printer_system.repository.PrinterRepository;
import com.cnpm.assignment.printer_system.repository.QAndARepository;
import com.cnpm.assignment.printer_system.repository.UserRepository;
import com.cnpm.assignment.printer_system.request.PayPackagePrintRequest;
import com.cnpm.assignment.printer_system.request.SearchPrinterStudentRequest;
import com.cnpm.assignment.printer_system.response.DetailBillResponse;
import com.cnpm.assignment.printer_system.response.DocumentResponse;
import com.cnpm.assignment.printer_system.response.HistoryPaymentResponse;
import com.cnpm.assignment.printer_system.response.PackagePrintResponse;
import com.cnpm.assignment.printer_system.response.PageResponse;
import com.cnpm.assignment.printer_system.response.PrinterStudentResponse;
import com.cnpm.assignment.printer_system.service.StudentService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private PrinterRepository printerRepository;

    @Autowired
    private PrinterDocumentRepository printerDocumentRepository;

    @Autowired
    private PageStudentRepository pageStudentRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private PrintPackageRepository printPackageRepository;

    @Autowired
    private BillPrintPackageRepository billPrintPackageRepository;

    @Autowired
    private QAndARepository qAndARepository;

    /**
     * Hàm để lấy tất cả các tài liệu (DocumentResponse) mà sinh viên đã upload lên
     * web
     * 
     * ! Phải phân trang dựa trên 2 thuộc tính:
     * ->pageNo: Trang thứ ...
     * ->pageSize: kích cỡ trang
     * 
     * B1: lấy user đang gửi request ra bằng Authentication và SecurityContextHolder
     * từ database (User) ra
     * -> nếu không tồn tại thì throw ra CNPMNotFoundException("Tài khoản không tồn
     * tại!")
     * !bằng email không phải username
     * B2: lấy các document đã upload của người dùng vừa request ra từ database
     * (entity Document)
     * B3: đối các đối tượng Document vừa lấy ra sang các đối tượng DocumentResponse
     * và trả về
     */
    @Override
    public Page<DocumentResponse> getUploadedDocument(Integer pageNo, Integer pageSize) {
        // TODO

        // Kiểm tra người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails != null) {
                User user = userRepository.findByEmailAndActive(userDetails.getUsername(), true)
                        .orElseThrow(() -> new CNPMNotFoundException("Tài khoản không tồn tại"));
                Pageable pageable = PageRequest.of(pageNo, pageSize);
                Student student = (Student) user;

                // Lấy document ra theo phân trang
                Page<Document> documents = documentRepository.findByStudentAndActive(student, true, pageable);

                // Chuyển đổi Các Document sang DocumentResponse để trả về
                List<DocumentResponse> responses = documents.stream()
                        .map(item -> DocumentResponse.builder().filename(item.getName()).id(item.getId())
                                .postDate(item.getPostDate()).size(item.getSize()).build())
                        .collect(Collectors.toList());

                // Trả về đối tượng Page<DocumentResponse>
                return new PageImpl<>(responses, pageable, Long.parseLong(String.valueOf(responses.size())));
            }
        }
        throw new CNPMNotFoundException("Tài khoản chưa xác thực. Vui lòng đăng nhập lại...!");
    }

    /**
     * Hàm để upload các tài liệu lên database (Document) mà sinh viên hiện tại đang
     * request
     * B1: lấy user đang gửi request ra bằng Authentication và SecurityContextHolder
     * từ database (User) ra
     * -> nếu không tồn tại thì throw ra CNPMNotFoundException("Tài khoản không tồn
     * tại!")
     * !bằng email không phải username
     * B2: đổi các MultipartFile sang Document và lưu vào database
     * 
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void uploadDocument(List<MultipartFile> documents) throws IOException {
        // TODO

        // Kiểm tra người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails != null) {
                User user = userRepository.findByEmailAndActive(userDetails.getUsername(), true)
                        .orElseThrow(() -> new CNPMNotFoundException("Tài khoản không tồn tại"));

                // Chuyển đối tượng MultipartFile sang Document và lưu vào database
                List<Document> newDocuments = new ArrayList<>();
                for (MultipartFile document : documents) {
                    // Lưu file lên cloudinary
                    Map result = cloudinary.uploader().upload(document.getBytes(), ObjectUtils.asMap(
                            "resource_type", "raw"));
                    newDocuments.add(
                            Document.builder().active(true).name(document.getOriginalFilename()).postDate(LocalDateTime.now())
                                    .size(document.getSize()).student((Student) user)
                                    .urlFile((String) result.get("url")).build());
                }

                // Lưu tất cả các Document vào database
                documentRepository.saveAll(newDocuments);
                return;
            }
        }
        throw new CNPMNotFoundException("Tài khoản chưa xác thực. Vui lòng đăng nhập lại...!");
    }

    /**
     * Hàm để tìm kiếm các máy in(Printer) đang còn hoạt động (ACTIVE:
     * PrinterStatusSPSO)
     * bằng các thuộc tính: address, statusStudent (trạng thái của máy in đối với
     * góc nhìn của học viên), id
     * -> Nếu không tìm thấy thì trả về list rỗng
     * -> Có thể tìm 1 hoặc 2 hoặc bằng cả 3 thuộc tính trên
     * -> Nếu cả 3 thuộc tính trên đều null hoặc rỗng thì trả về tất cả máy in đang
     * ACTIVE
     * 
     * ! Phải phân trang dựa trên 2 thuộc tính:
     * ->pageNo: Trang thứ ...
     * ->pageSize: kích cỡ trang (ví dụ 5 thì là trang có 5 đối tượng
     * PrinterStudentResponse trả về )
     * 
     * Nếu cần dùng đến thông tin người dùng hiện tại đang yêu cầu lấy máy in thì:
     * Lấy user đang gửi request ra bằng Authentication và SecurityContextHolder từ
     * database (User) ra
     * -> nếu không tồn tại thì throw ra CNPMNotFoundException("Tài khoản không tồn
     * tại!")
     * !bằng email không phải username
     */
    @Override
    public Page<PrinterStudentResponse> getPrinterActive(SearchPrinterStudentRequest request, Integer pageNo,
            Integer pageSize) {
        // TODO

        // Kiểm tra người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails != null) {
                User user = userRepository.findByEmailAndActive(userDetails.getUsername(), true)
                        .orElseThrow(() -> new CNPMNotFoundException("Tài khoản không tồn tại"));
                Pageable pageable = PageRequest.of(pageNo, pageSize);
                Student student = (Student) user;

                // Tìm kiếm máy in theo góc nhìn của sinh viên
                Page<Printer> printers = printerRepository.findByPrinterStudentRequest(request,
                        PrinterStatusSPSO.ACTIVE, pageable);
                List<PrinterStudentResponse> responses = printers.stream().map(item -> {
                    List<PrinterDocument> pdList = printerDocumentRepository
                            .findById_Document_StudentAndId_Printer(student, item, PageRequest.of(0, 2));
                    List<String> history = pdList.stream().map(it -> it.getId().getDocument().getName())
                            .collect(Collectors.toList());
                    return PrinterStudentResponse.builder().address(item.getAddress().getValue()).dateOfUse(item.getDateOfUse())
                            .historyUse(history).id(item.getId()).status(item.getPrinterStatusStudent().getValue())
                            .timeout(item.getTimeout()).build();
                }).collect(Collectors.toList());

                return new PageImpl<>(responses, pageable, responses.size());
            }
        }
        throw new CNPMNotFoundException("Tài khoản chưa xác thực. Vui lòng đăng nhập lại...!");
    }

    /**
     * Hàm lấy các trang còn lại của học viện hiện tại bao gồm các loại trang như
     * (Trang màu, trang thường, ...thêm nếu có loại trang mới)
     * 
     * Lấy các Page (bao gồm số lượng) của học viên ra từ database -> chuyển từ các
     * đối tượng Page sang PageResponse và gom lại thành danh sách -> trả về
     * 
     * Nếu cần dùng đến thông tin người dùng hiện tại đang yêu cầu lấy máy in thì:
     * Lấy user đang gửi request ra bằng Authentication và SecurityContextHolder từ
     * database (User) ra
     * -> nếu không tồn tại thì throw ra CNPMNotFoundException("Tài khoản không tồn
     * tại!")
     * !bằng email không phải username
     */
    @Override
    public List<PageResponse> getPageNow() {
        // TODO

        // Kiểm tra người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails != null) {
                User user = userRepository.findByEmailAndActive(userDetails.getUsername(), true)
                        .orElseThrow(() -> new CNPMNotFoundException("Tài khoản không tồn tại"));
                Student student = (Student) user;
                List<PageStudent> pageStudents = pageStudentRepository.findById_Student(student);
                List<PageResponse> responses = pageStudents.stream().map(item -> {
                    return PageResponse.builder().idPage(item.getId().getPage().getId())
                            .quantityPage(item.getPageQuantity()).type(item.getId().getPage().getType().getValue())
                            .build();
                }).collect(Collectors.toList());
                return responses;
            }
        }
        throw new CNPMNotFoundException("Tài khoản chưa xác thực. Vui lòng đăng nhập lại...!");
    }

    /**
     * Hàm lấy danh sách các bill của học viên hiện tại
     * Lấy các Bill của học viên hiện tại ra rồi đổi sang
     * List<HistoryPaymentResponse> để trả về
     * 
     * ! Phải phân trang dựa trên 2 thuộc tính:
     * ->pageNo: Trang thứ ...
     * ->pageSize: kích cỡ trang
     * 
     * Nếu cần dùng đến thông tin người dùng hiện tại đang yêu cầu lấy máy in thì:
     * Lấy user đang gửi request ra bằng Authentication và SecurityContextHolder từ
     * database (User) ra
     * -> nếu không tồn tại thì throw ra CNPMNotFoundException("Tài khoản không tồn
     * tại!")
     * !bằng email không phải username
     */
    @Override
    public Page<HistoryPaymentResponse> getHistoryPayments(Integer pageNo, Integer pageSize) {
        // TODO

        // Kiểm tra người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails != null) {
                User user = userRepository.findByEmailAndActive(userDetails.getUsername(), true)
                        .orElseThrow(() -> new CNPMNotFoundException("Tài khoản không tồn tại"));
                Student student = (Student) user;
                Pageable pageable = PageRequest.of(pageNo, pageSize);
                Page<Bill> bills = billRepository.findByStudent(student, pageable);
                List<HistoryPaymentResponse> responsesList = bills.stream().map(item -> {
                    Long sumPage = 0L;
                    for (BillPrintPackage p : item.getBillPrintPackages()) {
                        sumPage += p.getId().getPrintPackage().getPageQuantity() * p.getPackageQuantity();
                    }
                    return HistoryPaymentResponse.builder().datePayment(item.getDatePayment()).idBill(item.getId())
                            .pageQuantity(sumPage).statusPayment(item.getStatus().getValue())
                            .totalPrice(item.getTotalPrice()).type("Thanh toán tài liệu").build();
                }).collect(Collectors.toList());
                return new PageImpl<>(responsesList, pageable, responsesList.size());
            }
        }
        throw new CNPMNotFoundException("Tài khoản chưa xác thực. Vui lòng đăng nhập lại...!");
    }

    /**
     * Hàm dùng để thanh toán các package cho học viên
     * 
     * Khi học viên gửi đến các package cần mua thì hệ thống sẽ chuyển các package +
     * số lượng mỗi package đó vào 1 bill mới
     * -> Lưu Bill (Đang xử lý) đó lại vào database và chờ admin xử lý
     * 
     * Nếu cần dùng đến thông tin người dùng hiện tại đang yêu cầu lấy máy in thì:
     * Lấy user đang gửi request ra bằng Authentication và SecurityContextHolder từ
     * database (User) ra
     * -> nếu không tồn tại thì throw ra CNPMNotFoundException("Tài khoản không tồn
     * tại!")
     * !bằng email không phải username
     */
    @Override
    public void payPackagePrints(List<PayPackagePrintRequest> packages) {
        // TODO

        // Kiểm tra người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails != null) {
                User user = userRepository.findByEmailAndActive(userDetails.getUsername(), true)
                        .orElseThrow(() -> new CNPMNotFoundException("Tài khoản không tồn tại"));
                Student student = (Student) user;

                // Tính tổng giá tiền
                Bill bill = Bill.builder().datePayment(LocalDateTime.now()).status(BillStatus.PROCESSING).student(student)
                        .billPrintPackages(new ArrayList<>()).build();
                Long totalPrice = 0L;
                for (PayPackagePrintRequest p : packages) {
                    PrintPackage printPackage = printPackageRepository.findById(p.getIdPackagePrint())
                            .orElseThrow(() -> new CNPMNotFoundException("Gói in không tồn tại...!"));
                    bill.getBillPrintPackages()
                            .add(BillPrintPackage.builder().packageQuantity(p.getQuantityPackagePrint())
                                    .id(BillPrintPackageId.builder().bill(bill).printPackage(printPackage).build())
                                    .build());
                    totalPrice += (p.getQuantityPackagePrint()
                            * (printPackage.getPageQuantity() * printPackage.getPage().getPrice()));
                }
                bill.setTotalPrice(totalPrice);
                billRepository.save(bill);
                return;
            }
        }
        throw new CNPMNotFoundException("Tài khoản chưa xác thực. Vui lòng đăng nhập lại...!");
    }

    /**
     * Hàm này dùng để lấy danh sách chi tiết của các bill ra
     * 
     * Trả về danh sách DetailBillResponse
     * 
     * ! Phải phân trang dựa trên 2 thuộc tính:
     * ->pageNo: Trang thứ ...
     * ->pageSize: kích cỡ trang
     * 
     * Nếu cần dùng đến thông tin người dùng hiện tại đang yêu cầu lấy máy in thì:
     * Lấy user đang gửi request ra bằng Authentication và SecurityContextHolder từ
     * database (User) ra
     * -> nếu không tồn tại thì throw ra CNPMNotFoundException("Tài khoản không tồn
     * tại!")
     * !bằng email không phải username
     */
    @Override
    public Page<DetailBillResponse> getDetailBills(Integer pageNo, Integer pageSize) {
        // TODO

        // Kiểm tra người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails != null) {
                User user = userRepository.findByEmailAndActive(userDetails.getUsername(), true)
                        .orElseThrow(() -> new CNPMNotFoundException("Tài khoản không tồn tại"));
                Student student = (Student) user;
                Pageable pageable = PageRequest.of(pageNo, pageSize);
                Page<Bill> bills = billRepository.findByStudent(student, pageable);
                List<DetailBillResponse> detailBillResponses = bills.stream().map(item -> {
                    List<PrintPackage> printPackages = item.getBillPrintPackages().stream()
                            .map(it -> it.getId().getPrintPackage()).collect(Collectors.toList());
                    Long sumPage = 0L;
                    List<PackagePrintResponse> packagePrintResponses = new ArrayList<>();
                    for (PrintPackage p : printPackages) {
                        sumPage += p.getPageQuantity();

                        // Tạo các đối tượng PackagePrintResponse
                        Long quantityPackage = billPrintPackageRepository.findById_BillAndId_PrintPackage(item, p).orElseThrow(() -> new CNPMNotFoundException("Gói in không tồn tại trong bill này...!"))
                        .getPackageQuantity();
                        Long totalPricePackage = p.getPageQuantity() * p.getPage().getPrice();
                        packagePrintResponses.add(PackagePrintResponse.builder().idPackagePrint(p.getId())
                                .namePackage(p.getName()).quantityPackage(quantityPackage)
                                .singlePricePackage(totalPricePackage)
                                .totalPricePackage(quantityPackage * totalPricePackage)
                                .build());
                    }
                    return DetailBillResponse.builder().datePayment(item.getDatePayment()).billId(item.getId()).totalPageNumber(sumPage)
                            .totalPricePackages(item.getTotalPrice()).packagePrints(packagePrintResponses).build();
                }).collect(Collectors.toList());
                return new PageImpl<>(detailBillResponses, pageable, detailBillResponses.size());
            }
        }
        throw new CNPMNotFoundException("Tài khoản chưa xác thực. Vui lòng đăng nhập lại...!");
    }

    /**
     * Hàm gửi câu hỏi của học viên cho spso
     * 
     * Nếu idQAndA = null thì tạo chủ đề mới
     * Sau đó tạo câu hỏi trong QAndA mới đó
     * 
     * Còn nếu không null thì tạo câu hỏi mới trong QAndA đó
     */
    @Override
    public void createQuestion(Long idQAndA, String title, String message) {
        // TODO

        // Kiểm tra người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails != null) {
                User user = userRepository.findByEmailAndActive(userDetails.getUsername(), true)
                        .orElseThrow(() -> new CNPMNotFoundException("Tài khoản không tồn tại"));
                Student student = (Student) user;
                Content content = Content.builder().question(message).dateQuestion(LocalDateTime.now()).status(ContentStatus.NO_ANSWERED).build();
                QAndA qAndA;
                if(idQAndA != null){
                    qAndA = qAndARepository.findByIdAndStudent(idQAndA, student).orElseThrow(() -> new CNPMNotFoundException("Chủ để này của bạn không tồn tại...!"));
                }else{
                    qAndA = QAndA.builder().contents(new ArrayList<>()).title(title).student(student).build();
                }
                content.setId(ContentId.builder().qAndA(qAndA).build());
                qAndA.getContents().add(content);
                qAndARepository.save(qAndA);
                return;
            }
        }
        throw new CNPMNotFoundException("Tài khoản chưa xác thực. Vui lòng đăng nhập lại...!");
    }

    /**
     * Hàm xóa document của học viên theo id của Document
     * 
     * !Lưu ý: Xóa ở đây là chỉ chỉnh active của document đó về false chứ không được
     * xóa
     * trong database
     */
    @Override
    public void deleteUploadedDocument(Long idDocument) {
        // TODO
        // Kiểm tra người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails != null) {
                User user = userRepository.findByEmailAndActive(userDetails.getUsername(), true)
                        .orElseThrow(() -> new CNPMNotFoundException("Tài khoản không tồn tại"));
                Student student = (Student) user;
                Document document = documentRepository.findByStudentAndActiveAndId(student, true, idDocument).orElseThrow(() -> new CNPMNotFoundException("Tài liệu không tồn tại...!"));
                document.setActive(false);
                documentRepository.save(document);
                return;
            }
        }
        throw new CNPMNotFoundException("Tài khoản chưa xác thực. Vui lòng đăng nhập lại...!");
    }
}