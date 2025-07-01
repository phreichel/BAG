# Status of Identifiable Items

Each identifiable item shall have a maturity status
that can be changed to another statue depending on its maturit and current status.

The normal status shall be:

    * 01 DRAFT - The Item is actively workde on. Also the inital state for each Item
    * 02 PENDING - The Item is not released and put to a hold in backlog, but not discarded. Partly work might be available
    * 03 RELEASED - The Item is released and actively used within the development process outside of the item's scope
    * 04 DISCARDED - The Item is taken out of the development process.. might be revoked if necessary.

The normal transitions shall be:

    * DRAFT -> PRENDING
    * DRAFT -> RELEASED
    * DRAFT -> DISCARDED

    * PENDING -> DRAFT
    * PENDING -> RELEASED
    * PENDING -> DISCARDED

    * DISCARDED -> DRAFT
    * DISCARDED -> PENDING
    * DISCARDED -> RELEASED

The administrative transitions for special purposes shall be:

    * RELEASED -> DRAFT
    * RELEASED -> PENDING
    * RELEASED -> DISCARDED

How to handle versioning of an already existing released Item is not part of this subject
and needs to be discussed further.