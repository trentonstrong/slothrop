(ns slothrop.entity
  "Database entities for slothrop"
  (:use korma.core
        korma.db
        lobos.config))

(defdb bookcloud db)

(declare authors subjects)

(defentity books
  (pk :id)
  (table :books)
  (database bookcloud)

  (has-many authors {:fk :book_id})
  (has-many subjects {:fk :book_id}))

(defentity authors
  (pk :id)
  (table :books_authors)
  (database bookcloud)

  (has-one books {:fk :book_id}))

(defentity subjects
  (pk :id)
  (table :books_subjects)
  (database bookcloud)

  (has-one books {:fk :book_id}))