package blog.raubach.database.binding;

import blog.raubach.pojo.view.HikeStats;
import com.google.gson.Gson;
import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.Objects;

/**
 * @author Sebastian Raubach
 */
public class HikeStatsBinding implements Binding<JSON, HikeStats>
{
	@Override
	public Converter<JSON, HikeStats> converter()
	{
		Gson gson = new Gson();
		return new Converter<>()
		{
			@Override
			public HikeStats from(JSON o)
			{
				return o == null ? null : gson.fromJson(Objects.toString(o), HikeStats.class);
			}

			@Override
			public JSON to(HikeStats importJobDetails)
			{
				return importJobDetails == null ? null : JSON.json(gson.toJson(importJobDetails));
			}

			@Override
			public Class<JSON> fromType()
			{
				return JSON.class;
			}

			@Override
			public Class<HikeStats> toType()
			{
				return HikeStats.class;
			}
		};
	}

	@Override
	public void sql(BindingSQLContext<HikeStats> ctx)
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
	public void register(BindingRegisterContext<HikeStats> ctx)
		throws SQLException
	{
		ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
	}

	@Override
	public void set(BindingSetStatementContext<HikeStats> ctx)
		throws SQLException
	{
		ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
	}

	@Override
	public void set(BindingSetSQLOutputContext<HikeStats> ctx)
		throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void get(BindingGetResultSetContext<HikeStats> ctx)
		throws SQLException
	{
		ctx.convert(converter()).value(JSON.json(ctx.resultSet().getString(ctx.index())));
	}

	@Override
	public void get(BindingGetStatementContext<HikeStats> ctx)
		throws SQLException
	{
		ctx.convert(converter()).value(JSON.json(ctx.statement().getString(ctx.index())));
	}

	@Override
	public void get(BindingGetSQLInputContext<HikeStats> ctx)
		throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
}
