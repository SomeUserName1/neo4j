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
package org.neo4j.dmbs.database;

import java.util.Collections;
import java.util.Optional;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.neo4j.dbms.database.DatabaseExistsException;
import org.neo4j.dbms.database.StandaloneDatabaseContext;
import org.neo4j.graphdb.factory.module.GlobalModule;
import org.neo4j.graphdb.factory.module.edition.AbstractEditionModule;
import org.neo4j.kernel.database.Database;
import org.neo4j.kernel.impl.factory.GraphDatabaseFacade;
import org.neo4j.logging.Logger;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.neo4j.util.Preconditions.checkState;

public final class DefaultDatabaseManager extends AbstractDatabaseManager<StandaloneDatabaseContext>
{

    public DefaultDatabaseManager( GlobalModule globalModule, AbstractEditionModule edition, Logger log, GraphDatabaseFacade graphDatabaseFacade )
    {
        super( globalModule, edition, log, graphDatabaseFacade );
    }

    @Override
    public Optional<StandaloneDatabaseContext> getDatabaseContext( String name )
    {
        return Optional.ofNullable( databaseMap.get( name ) );
    }

    @Override
    public synchronized StandaloneDatabaseContext createDatabase( String databaseName )
    {
        requireNonNull( databaseName );
        checkState( databaseMap.size() < 2,
                format( "System and default database are already created. Fail to create another database: %s", databaseName ) );
        StandaloneDatabaseContext databaseContext = createNewDatabaseContext( databaseName );
        databaseMap.put( databaseName, databaseContext );
        return databaseContext;
    }

    @Override
    protected StandaloneDatabaseContext databaseContextFactory( Database database, GraphDatabaseFacade facade )
    {
        return new StandaloneDatabaseContext( database, facade );
    }

    @Override
    public void dropDatabase( String ignore )
    {
        throw new UnsupportedOperationException( "Default database manager does not support database drop." );
    }

    @Override
    public void stopDatabase( String ignore )
    {
        throw new UnsupportedOperationException( "Default database manager does not support database stop." );
    }

    @Override
    public void startDatabase( String databaseName )
    {
        throw new UnsupportedOperationException( "Default database manager does not support starting databases." );
    }
}
