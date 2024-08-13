package chukchuk.orderAPI.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class MemberDTO extends User {

    private String memberId, pw, name;

    private List<String> roleNames;

    public MemberDTO(String memberId, String pw, String name, List<String> roleNames) {

        super(memberId, pw, roleNames.stream().map(str ->
                new SimpleGrantedAuthority("ROLE_" + str)).collect(Collectors.toList()));

        this.memberId = memberId;
        this.pw = pw;
        this.name = name;
        this.roleNames = roleNames;
    }

    public Map<String, Object> getClaims() {

        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("memberId", memberId);
        dataMap.put("pw", pw);
        dataMap.put("name", name);
        dataMap.put("roleNames", roleNames);

        return dataMap;
    }

}
