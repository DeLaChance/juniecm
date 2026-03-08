import { createActionGroup, emptyProps, props } from '@ngrx/store';
import { ConfigurationItem } from '../configuration-item.model';

export const ConfigurationItemActions = createActionGroup({
  source: 'CI',
  events: {
    'Load Overview': emptyProps(),
    'Load Overview Success': props<{ items: ConfigurationItem[] }>(),
    'Load Overview Failure': props<{ error: string }>(),
    'Update Name': props<{ id: string; name: string }>(),
    'Update Name Success': props<{ id: string; name: string }>(),
    'Update Name Failure': props<{ error: string }>(),
    'Clear Store': emptyProps(),
  },
});
