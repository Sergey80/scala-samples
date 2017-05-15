package tests

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

// https://duckduckgo.com/?q=scala+aggregate+function&t=canonical&ia=qa&iax=1

class WordsSpec extends FlatSpec with Matchers with MockFactory {

  val text =
    """Hello,
      | how are you?
      | I'm good, how about you?
      | Me too - good good!
    """.stripMargin

  type Tag = (String, String)
  sealed trait TextElem
  case class Word(text: String, tags: Seq[Tag] = Seq()) extends TextElem
  case class Sentence(words: Seq[Word]) extends TextElem
  case class Chapter(title: String) extends TextElem
  case class Book(title: String, sentences: Seq[Sentence]) extends TextElem

  def words(text: String): Seq[Word] = {
    text.replaceAll("(\r\n)|\r|\n", "").
    split("\\s+|\\,").
      map(w => Word(w))
  }

  "words" should "extract words by..." in {
    val result = words(text)

    result.head should equal ( Word("Hello", Seq()) )
    result.last should equal ( Word("good!", Seq()) )

    result.size should equal(16)
  }


}