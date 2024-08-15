package chukchuk.orderAPI.repository;

import chukchuk.orderAPI.domain.Member;
import chukchuk.orderAPI.domain.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void insertTest() {

        Member member = Member.builder()
                .memberId("bg_admin")
                .name("유한경")
                .pw(passwordEncoder.encode("030201"))
                .build();

        member.addRole(MemberRole.ADMIN);

        memberRepository.save(member);
    }

    @Test
    public void insertTest2() {

        Member member = Member.builder()
                .memberId("imkeunho1")
                .name("임근호")
                .pw(passwordEncoder.encode("030201"))
                .build();

        member.addRole(MemberRole.USER);

        memberRepository.save(member);
    }

    @Test
    public void findByMemberIdTest() {

        String memberId = "bg_admin";

        Optional<Member> result = memberRepository.getWithRoles(memberId);

        Member member = result.orElseThrow();

        log.info("----------------");
        log.info(member);
        log.info(member.getMemberRoleList());
    }
}
