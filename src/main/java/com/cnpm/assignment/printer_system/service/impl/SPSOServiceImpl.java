package com.cnpm.assignment.printer_system.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

import com.cnpm.assignment.printer_system.entity.Content;
import com.cnpm.assignment.printer_system.entity.PageDocument;
import com.cnpm.assignment.printer_system.entity.PagePrinter;
import com.cnpm.assignment.printer_system.entity.Printer;
import com.cnpm.assignment.printer_system.entity.PrinterDocument;
import com.cnpm.assignment.printer_system.entity.QAndA;
import com.cnpm.assignment.printer_system.entity.SPSO;
import com.cnpm.assignment.printer_system.entity.Student;
import com.cnpm.assignment.printer_system.entity.User;
import com.cnpm.assignment.printer_system.enumeration.ContentStatus;
import com.cnpm.assignment.printer_system.exception.custom.CNPMNotFoundException;
import com.cnpm.assignment.printer_system.repository.PrinterDocumentRepository;
import com.cnpm.assignment.printer_system.repository.PrinterRepository;
import com.cnpm.assignment.printer_system.repository.QAndARepository;
import com.cnpm.assignment.printer_system.repository.StudentRepository;
import com.cnpm.assignment.printer_system.repository.UserRepository;
import com.cnpm.assignment.printer_system.request.EditPrinterRequest;
import com.cnpm.assignment.printer_system.request.SearchPrinterSPSORequest;
import com.cnpm.assignment.printer_system.response.PrintHistoryResponse;
import com.cnpm.assignment.printer_system.response.PrinterSPSOResponse;
import com.cnpm.assignment.printer_system.response.QAndAResponse;
import com.cnpm.assignment.printer_system.response.StudentResponse;
import com.cnpm.assignment.printer_system.service.SPSOService;

@Service
@Transactional
public class SPSOServiceImpl implements SPSOService {

    @Autowired
    private PrinterRepository printerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PrinterDocumentRepository printerDocumentRepository;

    @Autowired
    private QAndARepository qAndARepository;

    /**
     * Hàm để tìm kiếm các máy in(Printer)
     * bằng các thuộc tính: address, statusSPSO (trạng thái của máy in đối với góc
     * nhìn của SPSO), id
     * -> Nếu không tìm thấy thì trả về list rỗng
     * -> Có thể tìm 1 hoặc 2 hoặc bằng cả 3 thuộc tính trên
     * -> Nếu cả 3 thuộc tính trên đều null hoặc rỗng thì trả về tất cả máy in
     * 
     * ! Phải phân trang dựa trên 2 thuộc tính:
     * ->pageNo: Trang thứ ...
     * ->pageSize: kích cỡ trang (ví dụ 5 thì là trang có 5 đối tượng
     * PrinterSPSOResponse trả về )
     * 
     * Nếu cần dùng đến thông tin người dùng hiện tại đang yêu cầu lấy máy in thì:
     * Lấy user đang gửi request ra bằng Authentication và SecurityContextHolder từ
     * database (User) ra
     * -> nếu không tồn tại thì throw ra CNPMNotFoundException("Tài khoản không tồn
     * tại!")
     * !bằng email không phải username
     */
    @Override
    public Page<PrinterSPSOResponse> getPrinter(SearchPrinterSPSORequest request, Integer pageNo, Integer pageSize) {
        // TODO
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Printer> printers = printerRepository.findByPrinterSPSORequest(request, pageable);
        List<PrinterSPSOResponse> responses = printers.stream().map(item -> {
            Long totalPage = 0L;
            for (PagePrinter pagePrinter : item.getPagePrinters()) {
                totalPage += pagePrinter.getPageQuantity();
            }
            return PrinterSPSOResponse.builder().address(item.getAddress().getValue())
                    .lastMaintenanceDate(item.getLastMaintenanceDate()).pageQuantity(totalPage).id(item.getId())
                    .status(item.getPrinterStatusSPSO().getValue()).build();
        }).collect(Collectors.toList());
        return new PageImpl<>(responses, pageable, responses.size());
    }

    /**
     * Hàm để tìm kiếm các học sinh (Student) bằng tên
     * -> Nếu không tìm thấy thì trả về list rỗng
     * -> Nếu tên cần tìm là null hoặc rỗng thì trả về tất cả học viên
     * 
     * ! Phải phân trang dựa trên 2 thuộc tính:
     * ->pageNo: Trang thứ ...
     * ->pageSize: kích cỡ trang (ví dụ 5 thì là trang có 5 đối tượng
     * StudentResponse trả về )
     * 
     * Nếu cần dùng đến thông tin người dùng hiện tại đang yêu cầu lấy máy in thì:
     * Lấy user đang gửi request ra bằng Authentication và SecurityContextHolder từ
     * database (User) ra
     * -> nếu không tồn tại thì throw ra CNPMNotFoundException("Tài khoản không tồn
     * tại!")
     * !bằng email không phải username
     */
    @Override
    public Page<StudentResponse> searchStudent(String fullName, Integer pageNo, Integer pageSize) {
        // TODO
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Student> students = studentRepository.findByFullNameContaining(fullName, pageable);
        List<StudentResponse> responses = students.stream()
                .map(item -> StudentResponse.builder().sex(item.getSex().getValue()).id(item.getId())
                        .active(item.getActive()).birthday(item.getBirthday())
                        .email(item.getEmail()).fullName(item.getFullName())
                        .lastAccessedDate(item.getLastAccessedDate()).mssv(item.getMssv())
                        .phoneNumber(item.getPhoneNumber()).build())
                .collect(Collectors.toList());
        return new PageImpl<>(responses, pageable, responses.size());
    }

    /**
     * Hàm để lấy danh sách lịch sử in ấn của sinh viên theo id sinh viên
     * 
     * 
     * Trả về danh sách các lần in ấn của sinh viên (PrintHistoryResponse)
     * 
     * Nếu cần dùng đến thông tin người dùng hiện tại đang yêu cầu lấy máy in thì:
     * Lấy user đang gửi request ra bằng Authentication và SecurityContextHolder từ
     * database (User) ra
     * -> nếu không tồn tại thì throw ra CNPMNotFoundException("Tài khoản không tồn
     * tại!")
     * !bằng email không phải username
     */
    @Override
    public Page<PrintHistoryResponse> getHistoryPrint(Long studentId, Integer pageNo, Integer pageSize) {
        // TODO
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<PrinterDocument> printerDocuments = printerDocumentRepository.findById_Document_Student_Id(studentId,
                pageable);
        List<PrintHistoryResponse> responses = new ArrayList<>();
        for (PrinterDocument item : printerDocuments) {
            for (PageDocument pageDocument : item.getId().getDocument().getPageDocuments()) {
                responses.add(PrintHistoryResponse.builder().address(item.getId().getPrinter().getAddress().getValue())
                        .datePrint(item.getPrintDate()).idPrinter(item.getId().getPrinter().getId())
                        .nameDocument(item.getId().getDocument().getName()).quantityPage(pageDocument.getPageQuantity())
                        .typePage(pageDocument.getId().getPage().getType().getValue()).build());
            }
        }
        return new PageImpl<>(responses, pageable, responses.size());
    }

    /**
     * Hàm để đổi trạng thái tài khoản của học sinh, ví dụ:
     * tài khoản đang hoạt động thì sẽ khóa tài khoản đi, và ngược lại.
     */
    @Override
    public void changeActive(Long studentId) {
        // TODO
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CNPMNotFoundException("Học sinh không tồn tại...!"));
        student.setActive(!student.getActive());
        studentRepository.save(student);
    }

    /**
     * Hàm lấy ra danh sách tất cả các câu hỏi chưa trả lời và đã trả lời
     * 
     * Trả về List<QAndAResponse>
     * 
     * ! Phải phân trang dựa trên 2 thuộc tính:
     * ->pageNo: Trang thứ ...
     * ->pageSize: kích cỡ trang
     */
    @Override
    public Page<QAndAResponse> getHistoryQAndA(Integer pageNo, Integer pageSize) {
        // TODO
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByEmailAndActive(userDetails.getUsername(), true)
                    .orElseThrow(() -> new CNPMNotFoundException("Tài khoản không tồn tại...!"));
            if (user instanceof SPSO spso) {
                Page<QAndA> qAndAs = qAndARepository.findBySpso(spso, pageable);
                Page<QAndA> qAndAsNull = qAndARepository.findBySpso(null, pageable);
                List<QAndAResponse> responses = new ArrayList<>();
                responses.addAll(qAndAs.stream().map(item -> QAndAResponse.builder()
                        .dateQuestion(item.getContents().getLast().getDateQuestion()).firstQuestion(item.getTitle())
                        .idQAndA(item.getId()).nameOfStudent(item.getStudent().getFullName())
                        .status(item.getContents().getLast().getStatus().getValue()).build())
                        .collect(Collectors.toList()));
                responses.addAll(qAndAsNull.stream().map(item -> QAndAResponse.builder()
                        .dateQuestion(item.getContents().getLast().getDateQuestion()).firstQuestion(item.getTitle())
                        .idQAndA(item.getId()).nameOfStudent(item.getStudent().getFullName())
                        .status(item.getContents().getLast().getStatus().getValue()).build())
                        .collect(Collectors.toList()));
                return new PageImpl<>(responses, pageable, responses.size());
            }
        }
        throw new CNPMNotFoundException("Tài khoản chưa xác thực. Vui lòng đăng nhập lại...!");
    }

    /**
     * Hàm gửi câu trả lời của spso cho học viên
     * 
     * Câu trả lời được gắn với câu hỏi mới nhất trong chủ đề
     */
    @Override
    public void sendAnswer(Long idQAndA, String message) {
        // TODO
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByEmailAndActive(userDetails.getUsername(), true)
                    .orElseThrow(() -> new CNPMNotFoundException("Tài khoản không tồn tại...!"));
            if (user instanceof SPSO spso) {
                QAndA qAndA = qAndARepository.findById(idQAndA)
                        .orElseThrow(() -> new CNPMNotFoundException("Chủ để này không tồn tại...!"));
                Content content = qAndA.getContents().getLast();
                if (qAndA.getSpso() == null) {
                    qAndA.setSpso(spso);
                }
                content.setAnswer(message);
                content.setDateAnswer(LocalDateTime.now());
                content.setStatus(ContentStatus.HAS_ANSWERED);
                qAndARepository.save(qAndA);
                return;
            }
        }
        throw new CNPMNotFoundException("Tài khoản chưa xác thực. Vui lòng đăng nhập lại...!");
    }

    /**
     * Hàm này dành cho admin dùng để chỉnh sửa máy in
     * chỉnh sửa tình trạng của máy in hiện tại bằng id
     * 
     */
    @Override
    public void editPrinter(EditPrinterRequest request, Long idPrinter) {
        // TODO
        Printer printer = printerRepository.findById(idPrinter).orElseThrow(() -> new CNPMNotFoundException("Máy in không tồn tại...!"));
        printer.setLastMaintenanceDate(request.getLastMaintenanceDate());
        printer.getPagePrinters().getFirst().setPageQuantity(request.getPageQuantity() - printer.getPagePrinters().getLast().getPageQuantity());
        printer.setPrinterStatusSPSO(request.getStatus());
        printerRepository.save(printer);
    }
}