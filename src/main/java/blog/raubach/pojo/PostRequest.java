package blog.raubach.pojo;

import blog.raubach.database.codegen.enums.PostsType;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class PostRequest extends PaginatedRequest
{
	private Integer   year;
	private PostsType postType;
	private Integer   siteId;
	private Integer   hillId;
	private Integer   storyId;
	private Integer   relatedPostId;
}
