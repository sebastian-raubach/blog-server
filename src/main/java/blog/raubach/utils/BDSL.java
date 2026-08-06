package blog.raubach.utils;

import org.jooq.*;
import org.jooq.impl.*;

import static org.jooq.impl.SQLDataType.VARCHAR;

public class BDSL
{
	public static Field<String> jsonSearch(String searchValue, Field<?> field)
	{
		return CustomField.of("json_search", VARCHAR, ctx -> ctx.visit(DSL.field("json_search(lower({0}), 'one', CONCAT('%', lower({1}), '%'))", String.class, field, searchValue)));
	}

	public static Condition jsonContains(String searchValue, Field<?> field)
	{
		return CustomCondition.of(ctx -> ctx.visit(DSL.field("json_contains({0}, {1})", String.class, field, searchValue)));
	}
}
