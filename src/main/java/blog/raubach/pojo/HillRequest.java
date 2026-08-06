package blog.raubach.pojo;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class HillRequest extends PaginatedRequest
{
	private Integer postId;
	private String  hillName;
}
