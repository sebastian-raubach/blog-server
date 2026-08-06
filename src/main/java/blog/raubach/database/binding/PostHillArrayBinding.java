package blog.raubach.database.binding;

import blog.raubach.pojo.view.PostHill;
import com.google.gson.Gson;
import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.Objects;

/**
 * @author Sebastian Raubach
 */
public class PostHillArrayBinding implements Binding<JSON, PostHill[]>
{
	@Override
	public Converter<JSON, PostHill[]> converter()
	{
		Gson gson = new Gson();
		return new Converter<>()
		{
			@Override
			public PostHill[] from(JSON o)
			{
				return o == null ? null : gson.fromJson(Objects.toString(o), PostHill[].class);
			}

			@Override
			public JSON to(PostHill[] o)
			{
				return o == null ? null : JSON.json(gson.toJson(o));
			}

			@Override
			public Class<JSON> fromType()
			{
				return JSON.class;
			}

			@Override
			public Class<PostHill[]> toType()
			{
				return PostHill[].class;
			}
		};
	}

	@Override
	public void sql(BindingSQLContext<PostHill[]> ctx)
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
	public void register(BindingRegisterContext<PostHill[]> ctx)
		throws SQLException
	{
		ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
	}

	@Override
	public void set(BindingSetStatementContext<PostHill[]> ctx)
		throws SQLException
	{
		ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
	}

	@Override
	public void set(BindingSetSQLOutputContext<PostHill[]> ctx)
		throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void get(BindingGetResultSetContext<PostHill[]> ctx)
		throws SQLException
	{
		ctx.convert(converter()).value(JSON.json(ctx.resultSet().getString(ctx.index())));
	}

	@Override
	public void get(BindingGetStatementContext<PostHill[]> ctx)
		throws SQLException
	{
		ctx.convert(converter()).value(JSON.json(ctx.statement().getString(ctx.index())));
	}

	@Override
	public void get(BindingGetSQLInputContext<PostHill[]> ctx)
		throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
}
