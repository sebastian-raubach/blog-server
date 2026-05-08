package blog.raubach.database.binding;

import blog.raubach.database.codegen.enums.PostsitesGroundtype;
import com.google.gson.Gson;
import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.Objects;

/**
 * @author Sebastian Raubach
 */
public class SiteGroundTypeBinding implements Binding<JSON, PostsitesGroundtype>
{
	@Override
	public Converter<JSON, PostsitesGroundtype> converter()
	{
		Gson gson = new Gson();
		return new Converter<>()
		{
			@Override
			public PostsitesGroundtype from(JSON o)
			{
				return o == null ? null : gson.fromJson(Objects.toString(o), PostsitesGroundtype.class);
			}

			@Override
			public JSON to(PostsitesGroundtype importJobDetails)
			{
				return importJobDetails == null ? null : JSON.json(gson.toJson(importJobDetails));
			}

			@Override
			public Class<JSON> fromType()
			{
				return JSON.class;
			}

			@Override
			public Class<PostsitesGroundtype> toType()
			{
				return PostsitesGroundtype.class;
			}
		};
	}

	@Override
	public void sql(BindingSQLContext<PostsitesGroundtype> ctx)
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
	public void register(BindingRegisterContext<PostsitesGroundtype> ctx)
		throws SQLException
	{
		ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
	}

	@Override
	public void set(BindingSetStatementContext<PostsitesGroundtype> ctx)
		throws SQLException
	{
		ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
	}

	@Override
	public void set(BindingSetSQLOutputContext<PostsitesGroundtype> ctx)
		throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void get(BindingGetResultSetContext<PostsitesGroundtype> ctx)
		throws SQLException
	{
		ctx.convert(converter()).value(JSON.json(ctx.resultSet().getString(ctx.index())));
	}

	@Override
	public void get(BindingGetStatementContext<PostsitesGroundtype> ctx)
		throws SQLException
	{
		ctx.convert(converter()).value(JSON.json(ctx.statement().getString(ctx.index())));
	}

	@Override
	public void get(BindingGetSQLInputContext<PostsitesGroundtype> ctx)
		throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
}
