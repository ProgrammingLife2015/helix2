package tudelft.ti2806.pl3.data.graph;


public class Edge {
    public Edge(DataNode from, DataNode to) {
        this.from = from;
        this.to = to;
    }

    protected DataNode from;
    protected DataNode to;

    public DataNode getFrom() {
        return from;
    }

    public DataNode getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Edge [from=" + from.getId() + ", to=" + to.getId() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((from == null) ? 0 : from.hashCode());
        result = prime * result + ((to == null) ? 0 : to.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Edge other = (Edge) obj;
        if (from == null) {
            if (other.from != null) {
                return false;
            }
        } else if (!from.equals(other.from)) {
            return false;
        }
        if (to == null) {
            if (other.to != null) {
                return false;
            }
        } else if (!to.equals(other.to)) {
            return false;
        }
        return true;
    }

    public String getName() {
        return this.from.getId() + "_" + this.to.getId();
    }

    public int getFromId() {
        return getFrom().getId();
    }

    public int getToId() {
        return getTo().getId();
    }

}
