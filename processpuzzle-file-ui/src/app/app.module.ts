// Angular
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

// Third party components
import { MaterializeModule } from 'angular2-materialize';

// ProcessPuzzle components
import { AppComponent } from './app.component';
import { ProcesspuzzleFileUiLibModule } from 'processpuzzle-file-ui-lib';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    MaterializeModule,
    ProcesspuzzleFileUiLibModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
