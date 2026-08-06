package blog.raubach.pojo.view;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class HikeStats
{
	private Double duration;
	private Double distance;
	private Double ascent;
	private String gpx;
	private String elevationProfile;
	private String timeDistanceProfile;
}
