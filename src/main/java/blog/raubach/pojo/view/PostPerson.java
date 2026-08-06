package blog.raubach.pojo.view;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class PostPerson
{
	private Integer personId;
	private String personName;
}
