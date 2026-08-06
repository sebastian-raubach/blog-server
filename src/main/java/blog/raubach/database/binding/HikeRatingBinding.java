package blog.raubach.database.binding;

import blog.raubach.pojo.view.HikeRating;
import com.google.gson.Gson;
import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.Objects;

/**
 * @author Sebastian Raubach
 */
public class HikeRatingBinding implements Binding<JSON, HikeRating>
{
	@Override
	public Converter<JSON, HikeRating> converter()
	{
		Gson gson = new Gson();
		return new Converter<>()
		{
			@Override
			public HikeRating from(JSON o)
			{
				return o == null ? null : gson.fromJson(Objects.toString(o), HikeRating.class);
			}

			@Override
			public JSON to(HikeRating importJobDetails)
			{
				return importJobDetails == null ? null : JSON.json(gson.toJson(importJobDetails));
			}

			@Override
			public Class<JSON> fromType()
			{
				return JSON.class;
			}

			@Override
			public Class<HikeRating> toType()
			{
				return HikeRating.class;
			}
		};
	}

	@Override
	public void sql(BindingSQLContext<HikeRating> ctx)
		throws SQLException
	{
		// Depending on how you generate your SQL, you may need to explicitly distinguish
		// between jOOQ generating bind variables or inlined literals.
		if (ctx.render().paramType() == ParamType.INLINED)
			ctx.render().visit(DSL.inline(ctx.convert(converter()).value())).sql("");
		else
			ctx.render().sql("?");
	}

	@Override
	public void register(BindingRegisterContext<HikeRating> ctx)
		throws SQLException
	{
		ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
	}

	@Override
	public void set(BindingSetStatementContext<HikeRating> ctx)
		throws SQLException
	{
		ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
	}

	@Override
	public void set(BindingSetSQLOutputContext<HikeRating> ctx)
		throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void get(BindingGetResultSetContext<HikeRating> ctx)
		throws SQLException
	{
		ctx.convert(converter()).value(JSON.json(ctx.resultSet().getString(ctx.index())));
	}

	@Override
	public void get(BindingGetStatementContext<HikeRating> ctx)
		throws SQLException
	{
		ctx.convert(converter()).value(JSON.json(ctx.statement().getString(ctx.index())));
	}

	@Override
	public void get(BindingGetSQLInputContext<HikeRating> ctx)
		throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
}
