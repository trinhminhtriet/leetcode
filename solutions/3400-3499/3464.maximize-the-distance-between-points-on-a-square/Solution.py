class Solution:
    def maxDistance(self, side: int, points: List[List[int]], k: int) -> int:
        # Split points into the 4 lines
        pts = [[], [], [], []] # Left, Top, Right, Bottom
        for x, y in points:
            if x == 0 and y != 0: pts[0].append((x,y))
            elif x != 0 and y == side: pts[1].append((x,y))
            elif x == side and y != side: pts[2].append((x,y))
            else: pts[3].append((x,y))
        
        # Sort points in each line (Right and Bottom lines are
        # sorted in reverse to sort all points in clockwise dir)
        pts[0].sort()
        pts[1].sort()
        pts[2].sort(reverse=True)
        pts[3].sort(reverse=True)
        points = [*pts[0],*pts[1],*pts[2],*pts[3]] # Recombine points

        def check(d):
            de = deque([(points[0][0], points[0][1], points[0][0], points[0][1], 1)]) # Start processing from point 0
            ret = 1

            for i in range(1, len(points)):
                nx, ny = points[i]
                mx, my = nx, ny # Coordinates of path starting point
                mln = 1 # Current path length

                while len(de): # Try to pop points from the front of the deque
                    cx, cy, ox, oy, ln = de[0]
                    if abs(nx-cx) + abs(ny-cy) >= d: # Current point is at a distance d from the point at the deque head
                        if ln+1 >= mln and abs(nx-ox) + abs(ny-oy) >= d:
                            # If including the current point is an improvement in length AND
                            # If it is the last point, it is at a distance d from the start of the path - update values
                            mln = ln + 1
                            mx, my = ox, oy
                            ret = max(ret, mln)
                        de.popleft() # Always pop the deque head because we found the closest point at a distance d
                    else: break # No more points can be popped

                de.append((nx, ny, mx, my, mln)) # Add current point with path details to deque
            return ret >= k

        # Binary search the result
        l, r = 0, side
        while l <= r:
            m = (l+r) // 2
            if check(m): l = m + 1
            else: r = m - 1
        
        return r