import { inject, Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, of, switchMap } from 'rxjs';
import { ConfigurationItemService } from '../services/configuration-item.service';
import { ConfigurationItemActions } from './configuration-item.actions';

@Injectable()
export class ConfigurationItemEffects {
  private readonly actions$ = inject(Actions);
  private readonly ciService = inject(ConfigurationItemService);

  loadOverview$ = createEffect(() =>
    this.actions$.pipe(
      ofType(ConfigurationItemActions.loadOverview),
      switchMap(() =>
        this.ciService.getOverview().pipe(
          map((items) => ConfigurationItemActions.loadOverviewSuccess({ items })),
          catchError((error) =>
            of(ConfigurationItemActions.loadOverviewFailure({ error: error.message }))
          )
        )
      )
    )
  );

  updateName$ = createEffect(() =>
    this.actions$.pipe(
      ofType(ConfigurationItemActions.updateName),
      switchMap(({ id, name }) =>
        this.ciService.updateName(id, name).pipe(
          map(() => ConfigurationItemActions.updateNameSuccess({ id, name })),
          catchError((error) =>
            of(ConfigurationItemActions.updateNameFailure({ error: error.message }))
          )
        )
      )
    )
  );
}
