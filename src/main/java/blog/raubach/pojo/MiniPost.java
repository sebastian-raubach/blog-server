package blog.raubach.pojo;

import blog.raubach.database.codegen.enums.PostsType;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class MiniPost
{
	private Integer   id;
	private String    title;
	private Integer   primaryImageId;
	private String    primaryImagePath;
	private Byte      visible;
	private PostsType type;
}
