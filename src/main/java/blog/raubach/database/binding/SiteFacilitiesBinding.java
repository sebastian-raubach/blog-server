package blog.raubach.database.binding;

import blog.raubach.pojo.SiteFacilities;
import com.google.gson.Gson;
import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import java.sql.*;
import java.util.Objects;

/**
 * @author Sebastian Raubach
 */
public class SiteFacilitiesBinding implements Binding<JSON, SiteFacilities>
{
	@Override
	public Converter<JSON, SiteFacilities> converter()
	{
		Gson gson = new Gson();
		return new Converter<>()
		{
			@Override
			public SiteFacilities from(JSON o)
			{
				return o == null ? null : gson.fromJson(Objects.toString(o), SiteFacilities.class);
			}

			@Override
			public JSON to(SiteFacilities importJobDetails)
			{
				return importJobDetails == null ? null : JSON.json(gson.toJson(importJobDetails));
			}

			@Override
			public Class<JSON> fromType()
			{
				return JSON.class;
			}

			@Override
			public Class<SiteFacilities> toType()
			{
				return SiteFacilities.class;
			}
		};
	}

	@Override
	public void sql(BindingSQLContext<SiteFacilities> ctx)
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
	public void register(BindingRegisterContext<SiteFacilities> ctx)
		throws SQLException
	{
		ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
	}

	@Override
	public void set(BindingSetStatementContext<SiteFacilities> ctx)
		throws SQLException
	{
		ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
	}

	@Override
	public void set(BindingSetSQLOutputContext<SiteFacilities> ctx)
		throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void get(BindingGetResultSetContext<SiteFacilities> ctx)
		throws SQLException
	{
		ctx.convert(converter()).value(JSON.json(ctx.resultSet().getString(ctx.index())));
	}

	@Override
	public void get(BindingGetStatementContext<SiteFacilities> ctx)
		throws SQLException
	{
		ctx.convert(converter()).value(JSON.json(ctx.statement().getString(ctx.index())));
	}

	@Override
	public void get(BindingGetSQLInputContext<SiteFacilities> ctx)
		throws SQLException
	{
		throw new SQLFeatureNotSupportedException();
	}
}
