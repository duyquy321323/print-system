package com.cnpm.assignment.printer_system.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dữ liệu trả về sau khi login")
public class LoginResponse {
    @Schema(description = "Id của user", example = "1")
    private Long id;
    @Schema(description = "Avatar của user trả về link", example = "http://res.cloudinary.com/dau0dy9eu/image/upload/v1732504023/kziojfn1jgzn2t1zlcra.png")
    private String avatar;
    @Schema(description = "Tên đầy đủ của user", example = "Nguyễn Văn A")
    private String fullName;
    @Schema(description = "Mã Token của user vừa mới đăng nhập", example = "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InF1eS5kYW9xdXkzMjEzMjNAaGNtdXQuZWR1LnZuIiwic3ViIjoicXV5LmRhb3F1eTMyMTMyM0BoY211dC5lZHUudm4iLCJleHAiOjE3MzI1NTgyNTh9.7ddEbODiSxFo-fkDklniMxKWlW-mDRIkzWCs9TRffP8")
    private String token;
    @Schema(description = "Hạn của Token trên", example = "1732558258000")
    private Long expiryTime;
    private String role;
}