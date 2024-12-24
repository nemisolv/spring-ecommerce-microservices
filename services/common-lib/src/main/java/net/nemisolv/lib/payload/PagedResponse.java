package net.nemisolv.lib.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagedResponse<T> {
    private int pageNo;
    private int pageSize;
    private List<T> records;
    private String title;
    private long totalElements;
    private int totalPages;
    private boolean last;
}