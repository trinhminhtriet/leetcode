type Pos = (i32, i32);

impl Solution {
    pub fn count_combinations(pieces: Vec<String>, positions: Vec<Vec<i32>>) -> i32 {
        let src: Vec<Pos> = positions.iter().map(|p| (p[0], p[1])).collect();
        let reachable_pos: Vec<Vec<Pos>> = src
            .iter()
            .zip(pieces.iter())
            .map(|(pos, piece)| Self::all_reachable_positions(*pos, piece))
            .collect();

        Self::calculate_possible_moves(0, &mut vec![(0, 0); src.len()], &reachable_pos, &src)
    }

    fn calculate_possible_moves(i: usize, dst: &mut [Pos], reachable: &[Vec<Pos>], src: &[Pos]) -> i32 {
        if i == reachable.len() {
            return Self::is_moveable(dst, src);
        }

        reachable[i].iter().fold(0, |acc, &r| {
            if !dst[0..i].contains(&r) {
                dst[i] = r;
                acc + Self::calculate_possible_moves(i + 1, dst, reachable, src)
            } else {
                acc
            }
        })
    }

    fn is_moveable(dst: &[Pos], src: &[Pos]) -> i32 {
        let dir: Vec<Pos> = src
            .iter()
            .zip(dst.iter())
            .map(|(s, d)| ((d.0 - s.0).signum(), (d.1 - s.1).signum()))
            .collect();
        let mut src_pos = src.to_vec();
        Self::is_moveable_impl(&mut src_pos, dst, &dir)
    }

    fn is_moveable_impl(src: &mut [Pos], dst: &[Pos], dir: &[Pos]) -> i32 {
        if src == dst {
            return 1;
        }
        if (1..src.len()).any(|i| src[..i].contains(&src[i])) {
            return 0;
        }
        for i in 0..src.len() {
            if src[i] != dst[i] {
                src[i].0 += dir[i].0;
                src[i].1 += dir[i].1;
            }
        }
        Self::is_moveable_impl(src, dst, dir)
    }

    fn all_reachable_positions(src: Pos, piece: &str) -> Vec<Pos> {
        let directions = match piece {
            "rook" => vec![(1, 0), (-1, 0), (0, 1), (0, -1)],
            "bishop" => vec![(1, 1), (-1, -1), (1, -1), (-1, 1)],
            "queen" => vec![(1, 0), (-1, 0), (0, 1), (0, -1), (1, 1), (-1, -1), (1, -1), (-1, 1)],
            _ => panic!("Unknown piece type"),
        };

        let mut positions = vec![src];
        for &(dx, dy) in &directions {
            for step in 1..8 {
                let pos = (src.0 + dx * step, src.1 + dy * step);
                if (1..=8).contains(&pos.0) && (1..=8).contains(&pos.1) {
                    positions.push(pos);
                } else {
                    break;
                }
            }
        }
        positions
    }
}