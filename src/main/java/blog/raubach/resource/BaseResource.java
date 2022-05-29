package blog.raubach.resource;

import blog.raubach.pojo.PaginatedRequest;
import blog.raubach.utils.StringUtils;
import org.jooq.*;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;

import jakarta.ws.rs.*;
import java.util.logging.Logger;

public abstract class BaseResource extends ContextResource
{
	@QueryParam("searchTerm")
	protected String searchTerm;

	@DefaultValue("-1")
	@QueryParam("prevCount")
	protected long previousCount;

	@DefaultValue("0")
	@QueryParam("page")
	protected int currentPage;

	@DefaultValue("2147483647")
	@QueryParam("limit")
	protected int pageSize;

	@QueryParam("ascending")
	private Integer isAscending;

	@QueryParam("orderBy")
	protected String orderBy;

	protected void processRequest(PaginatedRequest request)
	{
		try
		{
			this.currentPage = request == null ? this.currentPage : request.getPage();
		}
		catch (NullPointerException | NumberFormatException e)
		{
			this.currentPage = 0;
		}
		try
		{
			this.pageSize = request == null ? this.pageSize : request.getLimit();
		}
		catch (NullPointerException | NumberFormatException e)
		{
			this.pageSize = Integer.MAX_VALUE;
		}
		try
		{
			this.searchTerm = request == null ? this.searchTerm : request.getSearchTerm();
		}
		catch (NullPointerException e)
		{
			this.searchTerm = null;
		}
		try
		{
			this.orderBy = request == null ? this.orderBy : request.getOrderBy();

			if (orderBy != null)
				orderBy = orderBy.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
		}
		catch (NullPointerException e)
		{
			this.orderBy = null;
		}
		try
		{
			this.isAscending = request == null ? this.isAscending : request.getAscending();
		}
		catch (NullPointerException | NumberFormatException e)
		{
			this.isAscending = 0;
		}
		try
		{
			this.previousCount = request == null ? this.previousCount : request.getPrevCount();
		}
		catch (NullPointerException | NumberFormatException e)
		{
			this.previousCount = -1L;
		}
	}

	protected <T extends Record> SelectForUpdateStep<T> setPaginationAndOrderBy(SelectOrderByStep<T> step)
	{
		if (isAscending != null && orderBy != null)
		{
			if (isAscending == 1)
				step.orderBy(DSL.field(getSafeColumn(orderBy)).asc());
			else
				step.orderBy(DSL.field(getSafeColumn(orderBy)).desc());
		}

		return step.limit(pageSize)
				   .offset(pageSize * currentPage);
	}

	protected static String getSafeColumn(String column)
	{
		if (StringUtils.isEmpty(column))
		{
			return null;
		}
		else
		{
			return column.replaceAll("[^a-zA-Z0-9._-]", "").replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
		}
	}
}
