package blog.raubach.database.binding;

import blog.raubach.pojo.view.PostImage;
import com.google.gson.Gson;
import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.Objects;

/**
 * @author Sebastian Raubach
 */
public class PostImageArrayBinding implements Binding<JSON, PostImage[]>
{
	@Override
	public Converter<JSON, PostImage[]> converter()
	{
		Gson gson = new Gson();
		return new Converter<>()
		{
			@Override
			public PostImage[] from(JSON o)
			{
				return o == null ? null : gson.fromJson(Objects.toString(o), PostImage[].class);
			}

			@Override
			public JSON to(PostImage[] o)
			{
				return o == null ? null : JSON.json(gson.toJson(o));
			}

			@Override
			public Class<JSON> fromType()
			{
				return JSON.class;
			}

			@Override
			public Class<PostImage[]> toType()
			{
				return PostImage[].class;
			}
		};
	}

	@Override
	public void sql(BindingSQLContext<PostImage[]> ctx)
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
	public void register(BindingRegisterContext<PostImage[]> ctx)
		throws SQLException
	{
		ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
	}

	@Override
	public void set(BindingSetStatementContext<PostImage[]> ctx)
		throws SQLException
	{
		ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
	}

	@Override
	public void set(BindingSetSQLOutputContext<PostImage[]> ctx)
		throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void get(BindingGetResultSetContext<PostImage[]> ctx)
		throws SQLException
	{
		ctx.convert(converter()).value(JSON.json(ctx.resultSet().getString(ctx.index())));
	}

	@Override
	public void get(BindingGetStatementContext<PostImage[]> ctx)
		throws SQLException
	{
		ctx.convert(converter()).value(JSON.json(ctx.statement().getString(ctx.index())));
	}

	@Override
	public void get(BindingGetSQLInputContext<PostImage[]> ctx)
		throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
}
