package main.smart.common.net;


public class PageParam
{
  private Integer len;
  private Integer offset;
  private Long totalNum;

  public Integer getLen()
  {
    return this.len;
  }

  public Integer getOffset()
  {
    return this.offset;
  }

  public Long getTotalNum()
  {
    return this.totalNum;
  }

  public void setLen(Integer paramInteger)
  {
    this.len = paramInteger;
  }

  public void setOffset(Integer paramInteger)
  {
    this.offset = paramInteger;
  }

  public void setTotalNum(Long paramLong)
  {
    this.totalNum = paramLong;
  }
}