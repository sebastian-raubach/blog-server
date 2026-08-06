package blog.raubach.pojo.view;

import blog.raubach.database.codegen.enums.*;
import blog.raubach.pojo.*;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class PostSite
{
	private Integer             siteId;
	private String              siteName;
	private String              siteDescription;
	private SitesSitetype       siteType;
	private Double              siteLatitude;
	private Double              siteLongitude;
	private SiteRating          siteRating;
	private SiteFacilities      siteFacilities;
	private PostsitesGroundtype groundType;
}
