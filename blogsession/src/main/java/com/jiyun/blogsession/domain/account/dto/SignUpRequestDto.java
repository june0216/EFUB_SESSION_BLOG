package com.jiyun.blogsession.domain.account.dto;

import com.jiyun.blogsession.domain.account.domain.Account;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SignUpRequestDto {

	//@NotEmpty, @Length, @Email은 validation이므로 어떤 데이터 (주로 사용자 또는 다른 서버의 request)의 값이 유효한지, 잘못된 내용이 있는지 확인하는 단계
	@NotEmpty(message = "이메일은 필수입니다.") //@NotBlank 는 null 과 "" 과 " " 모두 허용하지 않는다.
	@Email(message = "유효하지 않은 이메일 형식입니다.",
			regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
	private String email;

	@NotEmpty(message = "비밀번호는 필수입니다.")
	private String password;

	@NotEmpty(message = "닉네임은 필수입니다. ")
	@Column(nullable = false, length = 16)
	private String nickname;

	@Builder
	public SignUpRequestDto(String email, String nickname, String password) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
	}

	public Account toEntity() {
		return Account.builder()
				.email(this.email)
				.password(this.password)
				.nickname(this.nickname)
				.build();
	}


}
