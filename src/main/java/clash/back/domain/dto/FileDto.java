package clash.back.domain.dto;

import lombok.Builder;

@Builder
public class FileDto implements IOutputDto<String> {
    String fileName;

    @Override
    public IOutputDto<String> toDto(String fileName) {
        return FileDto.builder().fileName(fileName).build();
    }
}
