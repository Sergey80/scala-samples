package caseclasses

import shapeless._

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// THE IMPLICIT CLASS THAT ALLOWS TO CALL #copy ON ANY SEALED TRAIT INSTANCE
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

object copySyntax {

  implicit class CopySyntax[TypeToCopy](thingToCopy: TypeToCopy) {

    object copy extends RecordArgs {
      def applyRecord[RecordOfFieldsAndValues <: HList]
      (newFieldsAndValue: RecordOfFieldsAndValues)
      (implicit update: UpdaterFromRecord[TypeToCopy, RecordOfFieldsAndValues]): TypeToCopy = update(thingToCopy, newFieldsAndValue)
    }

  }

}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// TYPE CLASS DECLARATION : IF WE HAVE SOMETHING TO COPY AND A TYPE-CHECKED LIST OF NEW FIELDS AND VALUES, WE
// RETURN AN INSTANCE OF THE SAME TYPE.
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

trait UpdaterFromRecord[TypeToCopy, RecordOfFieldsAndValues <: HList] {
  def apply(thingToCopy: TypeToCopy, newFieldsAndValues: RecordOfFieldsAndValues): TypeToCopy
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// THIS OBJECT CONTAINS THE IMPLEMENTATION OF THE COMPILE-TIME GENERATION OF THE UPDATER BY IMPLICIT INDUCTION
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

object UpdaterFromRecord {

  import ops.record._

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // CASE 1 : WE HAVE TWO RECORDS (lists) OF FIELDS AND VALUES, THE SECOND ONE GETS MERGED INTO THE FIRST ONE
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  implicit def generateUpdaterFromMerger[RecordOfFieldsAndValues1 <: HList, RecordOfFieldsAndValues2 <: HList]
  (implicit merger: Merger.Aux[RecordOfFieldsAndValues1, RecordOfFieldsAndValues2, RecordOfFieldsAndValues1]): UpdaterFromRecord[RecordOfFieldsAndValues1, RecordOfFieldsAndValues2] =
    new UpdaterFromRecord[RecordOfFieldsAndValues1, RecordOfFieldsAndValues2] {
      def apply(baseRecord: RecordOfFieldsAndValues1, updatedValues: RecordOfFieldsAndValues2): RecordOfFieldsAndValues1 = merger(baseRecord, updatedValues)
    }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // CASE 2 : WE HAVE AN INSTANCE OF SEALED TRAIT WITHOUT ANY DECLARED MEMBER : WE JUST RETURN THE INSTANCE AS IT DOESN'T
  // HAVE ANY FIELD (this case exists only as a base case for recursive construction)
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  implicit def generateUpdaterForSealedTraitWithNoMember[RecordOfFieldsAndValues <: HList]: UpdaterFromRecord[CNil, RecordOfFieldsAndValues] =
    new UpdaterFromRecord[CNil, RecordOfFieldsAndValues] {
      def apply(t: CNil, r: RecordOfFieldsAndValues): CNil = t
    }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // CASE 3 : WE HAVE A WAY TO UPDATE AN INSTANCE OF AN EXTENSION OF A SEALED TRAIT (the head), AND A WAY TO UPDATE
  // INSTANCES OF THE OTHER EXTENSIONS (the tail) OF THE SEALED TRAIT. THIS IS SIMPLE RECURSIVE CONSTRUCTION, LIKE YOU
  // WOULD DO ON LISTS, BUT AT TYPE LEVEL
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  implicit def recursivelyGenerateUpdaterForGenericRepresentationOfSealedTrait[SealedMember, SealedMembers <: Coproduct, RecordOfFieldsAndValues <: HList]
  (implicit
   updaterForSealedMember: Lazy[UpdaterFromRecord[SealedMember, RecordOfFieldsAndValues]],
   recursiveUpdatedForTheRest: Lazy[UpdaterFromRecord[SealedMembers, RecordOfFieldsAndValues]]
  ): UpdaterFromRecord[SealedMember :+: SealedMembers, RecordOfFieldsAndValues] =
    new UpdaterFromRecord[SealedMember :+: SealedMembers, RecordOfFieldsAndValues] {
      def apply(thingToCopy: SealedMember :+: SealedMembers, newFieldsAndValues: RecordOfFieldsAndValues): SealedMember :+: SealedMembers = thingToCopy match {
        case Inl(head) => Inl(updaterForSealedMember.value(head, newFieldsAndValues))
        case Inr(tail) => Inr(recursiveUpdatedForTheRest.value(tail, newFieldsAndValues))
      }
    }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // CASE 4 : WE HAVE A WAY TO UPDATE A RECORD (see case 1) THAT MATCHES THE SHAPE OF A CASE CLASS, AND A WAY TO
  // TRANSFORM A CASE CLASS INSTANCE FROM AND TO SUCH A RECORD. WE CAN GENERATE THE UPDATER FOR THIS CASE CLASS.
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  implicit def generateUpdaterForGenericRepresentationOfCaseClass[CaseClassToCopy, RecordOfFieldsAndValues <: HList, GenericRepresentationOfTypeToCopy <: HList]
  (implicit
   caseClassChecker: HasProductGeneric[CaseClassToCopy],
   caseClassToGenericTransformer: LabelledGeneric.Aux[CaseClassToCopy, GenericRepresentationOfTypeToCopy],
   updaterForGenericRepresentation: Lazy[UpdaterFromRecord[GenericRepresentationOfTypeToCopy, RecordOfFieldsAndValues]]
  ): UpdaterFromRecord[CaseClassToCopy, RecordOfFieldsAndValues] =
    new UpdaterFromRecord[CaseClassToCopy, RecordOfFieldsAndValues] {
      def apply(thingToCopy: CaseClassToCopy, newFieldsAndValues: RecordOfFieldsAndValues): CaseClassToCopy = {
        val genericRepresentationOfThingToCopy: GenericRepresentationOfTypeToCopy = caseClassToGenericTransformer.to(thingToCopy)
        val genericRepresentationWithUpdatedValues: GenericRepresentationOfTypeToCopy
        = updaterForGenericRepresentation.value(genericRepresentationOfThingToCopy, newFieldsAndValues)
        val finalResultOfCopy: CaseClassToCopy = caseClassToGenericTransformer.from(genericRepresentationWithUpdatedValues)
        finalResultOfCopy
      }
    }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // CASE 5: WE HAVE A WAY TO UPDATE A GENERIC REPRESENTATION OF A SEALED TRAIT (see case 2 and 3) AND A WAY TO
  // TRANSFORM A SEALED TRAIT INSTANCE FROM AND TO SUCH A REPRESENTATION. WE CAN GENERATE THE UPDATER FOR THIS SEALED
  // TRAIT.
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  implicit def generateUpdaterForSealedTrait[SealedTraitToCopy, RecordOfFieldsAndValues <: HList, GenericRepresentationOfSealedTrait <: Coproduct]
  (implicit
   sealedTraitChecker: HasCoproductGeneric[SealedTraitToCopy],
   sealedTraitToGenericTransformer: Generic.Aux[SealedTraitToCopy, GenericRepresentationOfSealedTrait],
   updaterForGenericRepresentation: Lazy[UpdaterFromRecord[GenericRepresentationOfSealedTrait, RecordOfFieldsAndValues]]
  ): UpdaterFromRecord[SealedTraitToCopy, RecordOfFieldsAndValues] =
    new UpdaterFromRecord[SealedTraitToCopy, RecordOfFieldsAndValues] {
      def apply(thingToCopy: SealedTraitToCopy, newFieldsAndValues: RecordOfFieldsAndValues): SealedTraitToCopy = {
        val genericRepresentationOfThingToCopy: GenericRepresentationOfSealedTrait = sealedTraitToGenericTransformer.to(thingToCopy)
        val genericRepresentationWithUpdatedValues: GenericRepresentationOfSealedTrait
        = updaterForGenericRepresentation.value(genericRepresentationOfThingToCopy, newFieldsAndValues)
        val finalResultOfCopy: SealedTraitToCopy = sealedTraitToGenericTransformer.from(genericRepresentationWithUpdatedValues)
        finalResultOfCopy
      }
    }
}
