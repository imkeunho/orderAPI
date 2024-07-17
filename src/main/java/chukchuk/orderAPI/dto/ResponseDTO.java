package chukchuk.orderAPI.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ResponseDTO<E> {

    private List<E> dtoList;

    @Builder(builderMethodName = "setDTO")
    public ResponseDTO(List<E> dtoList) {
        this.dtoList = dtoList;
    }
}
