package blog.raubach.pojo.view;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class HikeRating
{
	private Short   weather;
	private Short   path;
	private Short   view;
}
