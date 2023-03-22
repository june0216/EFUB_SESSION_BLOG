# EFUB_SESSION_BLOG
EFUB 백엔드 세션에 필요한 블로그 코드 입니다. 
### 참고

<aside>
💡 1) 원래 프로젝트를 할 때 안전하게 토큰 등의 정보를 통해 유저의 정보를 알아낼 수 있지만 지금은 스프링 시큐리티 , 토큰 적용 전 단계이므로 URL 혹은 requestBody에 회원 id를 주고 받는 것으로 설정했습니다. 나중에 프로젝트할 때는 이렇게 회원 id를 노출하는 방법을 그대로 사용하는 보안상 문제가 있습니다. 숫자 Sequence(값 증가형) PK 중에는 그 자체가 외부 노출이 되면 이것이 노출되면 가입자 수나, 현재 주문 갯수 등을 외부에 노출시키는 것이 되기 때문입니다. 이를 방지하기 위한 방법을 다양하니 고민하시고 프로젝트에 적용해보시길 권장합니다!

</aside>

<aside>
💡 2) 아직 로그인 기능 구현 전이므로 데이터를 주고 받을 때 어쩔 수 없이 사용자의 id를 넣어야 하는 케이스가 있었다는 점을 감안해주세요!

</aside>

# 🍏1주차

# Accounts

[Accounts](https://www.notion.so/458d7a3b49484de5a309d26b4315e1d4)

# 🍏2주차

# Posts

[Posts](https://www.notion.so/477590c533b74496a936f15529266b27)

# 🍏3주차

[Posts (수정버전)](https://www.notion.so/393a14bed8294981962cc78cfa18e24d)

# Comments

[Comments](https://www.notion.so/04e649fe47354aaa969fdd6fb15bce22)
