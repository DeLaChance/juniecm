import { createFeature, createReducer, on } from '@ngrx/store';
import { ConfigurationItem } from '../configuration-item.model';
import { ConfigurationItemActions } from './configuration-item.actions';

export interface ConfigurationItemState {
  items: ConfigurationItem[];
  loading: boolean;
  error: string | null;
}

export const initialState: ConfigurationItemState = {
  items: [],
  loading: false,
  error: null,
};

export const configurationItemFeature = createFeature({
  name: 'configurationItems',
  reducer: createReducer(
    initialState,
    on(ConfigurationItemActions.loadOverview, (state) => ({
      ...state,
      loading: true,
      error: null,
    })),
    on(ConfigurationItemActions.loadOverviewSuccess, (state, { items }) => ({
      ...state,
      items,
      loading: false,
    })),
    on(ConfigurationItemActions.loadOverviewFailure, (state, { error }) => ({
      ...state,
      loading: false,
      error,
    })),
    on(ConfigurationItemActions.updateNameSuccess, (state, { id, name }) => ({
      ...state,
      items: updateItemName(state.items, id, name),
    })),
    on(ConfigurationItemActions.clearStore, () => initialState)
  ),
});

function updateItemName(items: ConfigurationItem[], id: string, name: string): ConfigurationItem[] {
  return items.map((item) => {
    if (item.id === id) {
      return { ...item, name };
    }
    if (item.children) {
      return { ...item, children: updateItemName(item.children, id, name) };
    }
    return item;
  });
}
