package searchclient;

class Pair<T> 
{
  T p1, p2;
  Pair()
  {
    //default constructor
  }
  Pair(T p1, T p2)
  {
    this.p1 = p1;
    this.p2 = p2;
  }
  void setValue(T a, T b)
  {
    this.p1 = a;
    this.p2 = b;
  }
  Pair getValue()
  {
    return this;
  }
  
}