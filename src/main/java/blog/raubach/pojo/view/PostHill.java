package blog.raubach.pojo.view;

import blog.raubach.database.codegen.enums.HillsType;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class PostHill
{
	private Integer   hillId;
	private String    hillName;
	private HillsType hillType;
	private Double    hillLatitude;
	private Double    hillLongitude;
	private Double    hillElevation;
	private Byte      hillSuccessful;
}
