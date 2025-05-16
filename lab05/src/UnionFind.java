public class UnionFind {
    // TODO: Instance variables
    int size;
    int parent[];


    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        size = N;
        parent = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = -1;
        }


    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE

        return -parent(find(v));
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        return parent[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        if(v > size-1 || v < 0){
            throw new IllegalArgumentException("item index out of range");
        }
        int p = v;
        while (parent[p] > 0){
            p = parent[p];
        }
        int root = p;

        while (parent[v] > 0){
            p = v;
            v = parent[v];
            parent[p] = root;
        }

        return root;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if(v1 == v2 || connected(v1, v2)){
            return;
        }
        else if(sizeOf(v1) > sizeOf(v2)){
            unionHelper(v1, v2);
        }
        else{
            unionHelper(v2, v1);
        }

    }
    // v2 to v1
    public void unionHelper(int v1, int v2){
        int root = find(v1);
        int add = -sizeOf(v2);
        parent[find(v2)] = root;
        parent[root] += add;
    }

}
