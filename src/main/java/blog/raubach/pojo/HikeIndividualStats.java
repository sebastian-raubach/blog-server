package blog.raubach.pojo;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class HikeIndividualStats extends HashMap<Integer, List<HikeIndividualStats.Section>>
{
	@Getter
	@Setter
	@Accessors(chain = true)
	@NoArgsConstructor
	@ToString
	public static class Section {
		private Double from;
		private Double to;
		private MovementType type;
	}

	public static enum MovementType {
		BIKE,
		WALK,
		RUN,
		TRAILER,
		SWIM,
		BACKPACK
	}
}
