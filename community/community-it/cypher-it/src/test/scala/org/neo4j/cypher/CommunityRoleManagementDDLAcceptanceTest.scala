/*
 * Copyright (c) 2002-2019 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cypher

import org.neo4j.configuration.GraphDatabaseSettings

class CommunityRoleManagementDDLAcceptanceTest extends CommunityDDLAcceptanceTestBase {

  test("should fail on showing roles from community") {
    // GIVEN
    selectDatabase(GraphDatabaseSettings.SYSTEM_DATABASE_NAME)

    // THEN
    assertFailure("SHOW ROLES", "Unsupported management command: SHOW ROLES")
  }

  test("should fail on showing roles with users from community") {
    // GIVEN
    selectDatabase(GraphDatabaseSettings.SYSTEM_DATABASE_NAME)

    // THEN
    assertFailure("SHOW POPULATED ROLES WITH USERS", "Unsupported management command: SHOW POPULATED ROLES WITH USERS")
  }

  test("should fail on creating role from community") {
    // GIVEN
    selectDatabase(GraphDatabaseSettings.SYSTEM_DATABASE_NAME)

    // THEN
    assertFailure("CREATE ROLE foo", "Unsupported management command: CREATE ROLE foo")
  }

  test("should fail on creating role as copy of non-existing role with correct error message") {
    // GIVEN
    selectDatabase(GraphDatabaseSettings.SYSTEM_DATABASE_NAME)

    // THEN
    assertFailure("CREATE ROLE foo AS COPY OF bar", "Unsupported management command: CREATE ROLE foo AS COPY OF bar")
  }

  test("should fail on dropping non-existing role from community") {
    // GIVEN
    selectDatabase(GraphDatabaseSettings.SYSTEM_DATABASE_NAME)

    // THEN
    assertFailure("DROP ROLE foo", "Unsupported management command: DROP ROLE foo")
  }

  test("should fail on granting role to user from community") {
    // GIVEN
    selectDatabase(GraphDatabaseSettings.SYSTEM_DATABASE_NAME)

    // THEN
    assertFailure("GRANT ROLE reader TO neo4j", "Unsupported management command: GRANT ROLE reader TO neo4j")
  }

  test("should fail on revoking non-existing role to user with correct error message") {
    // GIVEN
    selectDatabase(GraphDatabaseSettings.SYSTEM_DATABASE_NAME)

    // THEN
    assertFailure("REVOKE ROLE custom FROM neo4j", "Unsupported management command: REVOKE ROLE custom FROM neo4j")
  }
}
