package main.smart.common.net;


import java.util.List;

public class PagedResponse<T>
{
  private PageParam pageParam;
  private List<T> result;

  public PageParam getPageParam()
  {
    return this.pageParam;
  }

  public List<T> getResult()
  {
    return this.result;
  }

  public void setPageParam(PageParam paramPageParam)
  {
    this.pageParam = paramPageParam;
  }

  public void setResult(List<T> paramList)
  {
    this.result = paramList;
  }
}
