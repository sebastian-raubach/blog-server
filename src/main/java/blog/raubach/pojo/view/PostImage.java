package blog.raubach.pojo.view;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class PostImage
{
	private Integer imageId;
	private String  imagePath;
	private Byte    imageIsPrimary;
	private String  imageDescription;
}
