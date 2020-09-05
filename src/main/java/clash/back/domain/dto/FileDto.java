package clash.back.domain.dto;

import lombok.Builder;

@Builder
public class FileDto implements IOutputDto<String>, IInputDto<String> {
    String fileName;

    @Override
    public IOutputDto<String> toDto(String fileName) {
        return FileDto.builder().fileName(fileName).build();
    }

    @Override
    public boolean isValid() {
        return !fileName.isEmpty();
    }

    @Override
    public String fromDto() {
        return fileName;
    }
}
