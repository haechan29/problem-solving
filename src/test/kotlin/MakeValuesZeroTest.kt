import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import programmers.Solution

class MakeValuesZeroTest: FunSpec({
    val solution = Solution()

    test("1") {
        val result = solution.solution(
            intArrayOf(-5, 0, 2, 1, 2),
            arrayOf(
                intArrayOf(0, 1),
                intArrayOf(3, 4),
                intArrayOf(2, 3),
                intArrayOf(0, 3)
            )
        )

        result shouldBe 9
    }

    test("2") {
        val result = solution.solution(
            intArrayOf(0, 1, 0),
            arrayOf(
                intArrayOf(0, 1),
                intArrayOf(1, 2)
            )
        )

        result shouldBe -1
    }
})