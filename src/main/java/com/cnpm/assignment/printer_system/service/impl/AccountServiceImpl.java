package com.cnpm.assignment.printer_system.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cnpm.assignment.printer_system.entity.QAndA;
import com.cnpm.assignment.printer_system.entity.SPSO;
import com.cnpm.assignment.printer_system.entity.Student;
import com.cnpm.assignment.printer_system.entity.User;
import com.cnpm.assignment.printer_system.exception.custom.CNPMNotFoundException;
import com.cnpm.assignment.printer_system.exception.custom.PasswordNotMatchException;
import com.cnpm.assignment.printer_system.repository.QAndARepository;
import com.cnpm.assignment.printer_system.repository.UserRepository;
import com.cnpm.assignment.printer_system.response.ContentResponse;
import com.cnpm.assignment.printer_system.response.InformationResponse;
import com.cnpm.assignment.printer_system.response.LoginResponse;
import com.cnpm.assignment.printer_system.service.AccountService;
import com.cnpm.assignment.printer_system.service.JwtToken;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private QAndARepository qAndARepository;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private Cloudinary cloudinary;

    /**
     * Hàm đăng nhập
     * Kiểm tra email có tồn tại không: nếu có thì kiểm tra password có đúng không
     * -> Đúng password thì tạo token cho user đó và trả về đối tượng LoginResponse
     * của User
     * -> Sai password thì throw ra exception PasswordNotMatchException("Mật khẩu
     * không chính xác!")
     * -> Sai email thì throw ra exception CNPMNotFoundException("Tài khoản không
     * tồn tại!")
     */
    @Override
    public LoginResponse login(String email, String password) {
        // TODO
        User user = userRepository.findByEmailAndActive(email, true)
                .orElseThrow(() -> new CNPMNotFoundException("Tài khoản không tồn tại!"));
        if (!user.getPassword().equals(password)) {
            throw new PasswordNotMatchException("Mật khẩu không chính xác!");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                email, password, userDetails.getAuthorities());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtToken.generateToken(user); // tạo token cho User;

        // Set ngày đăng nhập mới nhất cho User
        user.setLastAccessedDate(LocalDateTime.now());
        userRepository.save(user);

        String role = (user instanceof SPSO? "SPSO" : "STUDENT");

        return LoginResponse.builder().role(role).avatar(user.getUrlAvatar()).fullName(user.getFullName()).id(user.getId())
                .token(token).expiryTime(jwtToken.extractExpirationToken(token).getTime()).build();
    }

    /**
     * Hàm này để cập nhật avatar của người dùng hiện tại
     * 
     * Lấy user đang gửi request ra bằng Authentication và SecurityContextHolder từ
     * database (User) ra
     * -> nếu không tồn tại thì throw ra CNPMNotFoundException("Tài khoản không tồn
     * tại!")
     * !bằng email không phải username
     * 
     * @throws IOException
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void updateAvatar(MultipartFile avatar) throws IOException {
        // TODO
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails != null) {
                User user = userRepository.findByEmailAndActive(userDetails.getUsername(), true)
                        .orElseThrow(() -> new CNPMNotFoundException("Tài khoản không tồn tại!"));

                // Xóa avatar bằng id user trên cloud
                if (user.getUrlAvatar() != null && !user.getUrlAvatar().equals("")) {
                    cloudinary.uploader().destroy(user.getId().toString(), ObjectUtils.emptyMap());
                }

                // Tải avatar mới lên cloud
                Map infoAvatar = cloudinary.uploader().upload(avatar.getBytes(),
                        ObjectUtils.asMap("public_id", user.getId().toString()));
                String urlAvatar = (String) infoAvatar.get("url");

                // Lưu link avatar mới vào database cho User
                user.setUrlAvatar(urlAvatar);
                userRepository.save(user);
                return;
            }
        }
        throw new CNPMNotFoundException("Tài chưa xác thực. Vui lòng đăng nhập lại...!");
    }

    /**
     * Hàm này để lấy thông tin của người dùng hiện tại
     * Trả về đối tượng InformationResponse
     * 
     * Lấy user đang gửi Auto-generated method stub
     * throw new UnsupportedOperationException("Unimplemented method
     * 'deleteUploadedDocument'");request ra bằng Authentication và
     * SecurityContextHolder từ
     * database (User) ra
     * -> nếu không tồn tại thì throw ra CNPMNotFoundException("Tài khoản không tồn
     * tại!")
     * !bằng email không phải username
     */
    @Override
    public InformationResponse getInformation() {
        // TODO
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails != null) {
                User user = userRepository.findByEmailAndActive(userDetails.getUsername(), true)
                        .orElseThrow(() -> new CNPMNotFoundException("Tài khoản không tồn tại!"));
                InformationResponse response = InformationResponse.builder().birthday(user.getBirthday())
                        .email(user.getEmail())
                        .fullName(user.getFullName()).phoneNumber(user.getPhoneNumber()).sex(user.getSex().getValue())
                        .urlAvatar(user.getUrlAvatar()).build();
                if (user instanceof Student student) {
                    response.setMssv(student.getMssv());
                } else {
                    response.setMssv("");
                }
                return response;
            }
        }
        throw new CNPMNotFoundException("Tài chưa xác thực. Vui lòng đăng nhập lại...!");
    }

    /**
     * Hàm này để lấy các cặp câu hỏi + trả lời của 1 chủ đề ra bằng id của chủ để
     * đó
     * 
     * Trả về danh sách ContentResponse
     * 
     * @throws IOException
     */
    @Override
    public List<ContentResponse> getDetailQAndA(HttpServletResponse response, Long idQAndA) throws IOException {
        // TODO
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails;
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            userDetails = (UserDetails) authentication.getPrincipal();
            if (userDetails != null) {
                User user = userRepository.findByEmailAndActive(userDetails.getUsername(), true)
                        .orElseThrow(() -> new CNPMNotFoundException("Tài khoản không tồn tại!"));
                QAndA qAndA;
                if (user instanceof Student student) {
                    qAndA = qAndARepository.findByIdAndStudent(idQAndA, student)
                            .orElseThrow(() -> new CNPMNotFoundException("Chủ đề này của bạn không tồn tại...!"));
                } else if (user instanceof SPSO) {
                    qAndA = qAndARepository.findById(idQAndA)
                            .orElseThrow(() -> new CNPMNotFoundException("Chủ đề này không tồn tại...!"));
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return null;
                }
                List<ContentResponse> responses = qAndA.getContents().stream()
                        .map(item -> ContentResponse.builder().answer(item.getAnswer()).dateAnswer(item.getDateAnswer())
                                .dateQuestion(item.getDateQuestion())
                                .nameSPSO(item.getId().getQAndA().getSpso() != null? item.getId().getQAndA().getSpso().getFullName() : "")
                                .nameStudent(item.getId().getQAndA().getStudent() != null? item.getId().getQAndA().getStudent().getFullName() : "")
                                .question(item.getQuestion()).build())
                        .collect(Collectors.toList());
                return responses;
            }
        }
        throw new CNPMNotFoundException("Tài khoản chưa xác thực. Vui lòng đăng nhập lại...!");
    }
}