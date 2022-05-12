import java.util.TreeSet;

public class Elevator {
    private TreeSet<Integer> upRequest;
    private TreeSet<Integer> downRequest;
    private Integer floorNo; // will be updated by some embedded system in lift.
    private State state; // will control the life whether to move up, down or idle

    public Elevator() {
        this.upRequest = new TreeSet<>();
        this.downRequest = new TreeSet<>();
        this.floorNo = 0;
        this.state = State.IDLE;
    }

    public void run() {
        while (true) {
            if (State.MOVING_UP == state) {
                if (upRequest.contains(floorNo)) {
                    serveRequest(upRequest);
                    final Integer highestRequestedFloor = this.getHighestRequestedFloor();
                    if (highestRequestedFloor == null) {
                        this.state = State.IDLE;
                    } else if (highestRequestedFloor <= floorNo) {
                        this.state = State.MOVING_DOWN;
                    }
                }
                // if elevator is moving up then elevator need to go to the highest requested floor.
            } else if (State.MOVING_DOWN == state) {
                serveRequest(downRequest);
                final Integer lowestRequestedFloor = this.getLowestRequestedFloor();
                if (lowestRequestedFloor == null) {
                    this.state = State.IDLE;
                } else if (lowestRequestedFloor >= floorNo) {
                    this.state = State.MOVING_UP;
                }
                // if elevator is moving down then it should reach to the lowest requested floor.
            } else {
                final Integer highestRequestedFloor = this.getHighestRequestedFloor();
                final Integer lowestRequestedFloor = this.getLowestRequestedFloor();
                if (!(highestRequestedFloor == null || lowestRequestedFloor == null)) {
                    int distToHighestRequestedFloor = Math.abs(highestRequestedFloor - floorNo);
                    int distToLowestRequestedFloor = Math.abs(lowestRequestedFloor - floorNo);
                    if (distToHighestRequestedFloor <= distToLowestRequestedFloor) {
                        this.state = State.MOVING_UP;
                    } else {
                        this.state = State.MOVING_DOWN;
                    }
                }
            }
        }
    }

    private void serveRequest(TreeSet<Integer> requestSet) {
        final State prevState = this.state;
        this.state = State.IDLE;
        openDoor(); // take care of Thread.sleep()
        requestSet.remove(floorNo);
        closeDoor();
        this.state = prevState;
    }

    private Integer getHighestRequestedFloor() {
        if (upRequest.size() != 0 && downRequest.size() != 0) {
            return Math.max(upRequest.last(), downRequest.last());
        } else if (upRequest.size() != 0) {
            return upRequest.last();
        } else if (downRequest.size() != 0) {
            return downRequest.last();
        }

        return null;
    }

    private Integer getLowestRequestedFloor() {
        if (upRequest.size() != 0 && downRequest.size() != 0) {
            return Math.min(upRequest.first(), downRequest.first());
        } else if (upRequest.size() != 0) {
            return upRequest.first();
        } else if (downRequest.size() != 0) {
            return downRequest.first();
        }

        return null;
    }

    private void openDoor() {}
    private void closeDoor() {}
}
