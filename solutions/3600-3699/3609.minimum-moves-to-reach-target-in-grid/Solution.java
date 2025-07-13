class Solution {
        public int minMoves(int sx, int sy, int tx, int ty) {
        if (sx == 0 && sy == 0) {
            return (tx == 0 && ty == 0) ? 0 : -1;
        }

        int res = 0;
        while (sx != tx || sy != ty) {
            if (sx > tx || sy > ty) {
                return -1;
            }
            res++;
            if (tx > ty) {
                if (tx > ty * 2) {
                    if (tx % 2 != 0) {
                        return -1;
                    }
                    tx /= 2;
                } else {
                    tx -= ty;
                }
            } else if (tx < ty) {
                if (ty > tx * 2) {
                    if (ty % 2 != 0) {
                        return -1;
                    }
                    ty /= 2;
                } else {
                    ty -= tx;
                }
            } else {
                if (sx == 0) {
                    tx = 0;
                } else if (sy == 0) {
                    ty = 0;
                } else {
                    return -1;
                }
            }
        }
        return res;
    }
}
