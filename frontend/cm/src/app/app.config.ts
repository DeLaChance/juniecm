import { ApplicationConfig, provideBrowserGlobalErrorListeners } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideStore } from '@ngrx/store';
import { provideEffects } from '@ngrx/effects';

import { routes } from './app.routes';
import { configurationItemFeature } from './configuration-item/state/configuration-item.reducer';
import { ConfigurationItemEffects } from './configuration-item/state/configuration-item.effects';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideHttpClient(),
    provideAnimationsAsync(),
    provideStore({
      [configurationItemFeature.name]: configurationItemFeature.reducer,
    }),
    provideEffects(ConfigurationItemEffects),
  ]
};
