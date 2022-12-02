// #combinations, #subset, #powerset
val data = List(1,2,3)
// 1
Set(1,2,3).subsets.map(_.toList).toList
// 2
val res  = for(i <- 1 to data.size) yield List(1, 2, 3).combinations(i).toList
// 3
def powerset[A](s: Set[A]) = s.foldLeft(
                            Set(Set.empty[A])) 
                            { case (acc, v) =>  acc ++ acc.map(_ + v)  }
powerset(data.toSet) // Set(Set(), Set(1, 3), Set(2), Set(1, 2), Set(2, 3), Set(3), Set(1, 2, 3), Set(1))
// if list has unique values:
data.foldLeft(List(List.empty[Int]) )
                          { case (acc, x) => acc ++ acc.map(x :: _)  }

