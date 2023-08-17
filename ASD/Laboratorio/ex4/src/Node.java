public class Node {
  String place1;
  String place2;
  float distance;

  public Node(String place1, String place2, float distance) {
    this.place1 = place1;
    this.place2 = place2;
    this.distance = distance;
  }

  public String getPlace1() {
    return place1;
  }

  public String getPlace2() {
    return place2;
  }

  public float getDistance() {
    return distance;
  }
}
