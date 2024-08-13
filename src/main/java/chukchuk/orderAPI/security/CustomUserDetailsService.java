package chukchuk.orderAPI.security;

import chukchuk.orderAPI.domain.Member;
import chukchuk.orderAPI.dto.MemberDTO;
import chukchuk.orderAPI.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("---------loadUserByUsername---------{}", username);

        Optional<Member> result = memberRepository.getWithRoles(username);

        Member member = result.orElseThrow(() -> new UsernameNotFoundException("Not Found : " + username));

        MemberDTO memberDTO = new MemberDTO(
                member.getMemberId(),
                member.getPw(),
                member.getName(),
                member.getMemberRoleList().stream().map(Enum::name).toList());

        log.info(memberDTO);

        return memberDTO;
    }
}
