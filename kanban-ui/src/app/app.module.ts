import {BrowserModule} from '@angular/platform-browser';
import {LOCALE_ID, NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatListModule} from '@angular/material/list';
import {MatButtonModule} from '@angular/material/button';
import {MatDialogModule, MatInputModule, MatSelectModule} from '@angular/material';
import {DragDropModule} from '@angular/cdk/drag-drop';

import {HomeComponent} from './home/home.component';
import {ProjectComponent} from './project/project.component';
import {CardDialogComponent} from './dialogs/card-dialog/card-dialog.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ProjectDialogComponent} from './dialogs/project-dialog/project-dialog.component';
import {DateFormatPipe} from './dialogs/card-dialog/DateFormat.pipe';
import {registerLocaleData} from '@angular/common';
import localeRu from '@angular/common/locales/ru';

registerLocaleData(localeRu, 'ru');
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ProjectComponent,
    CardDialogComponent,
    ProjectDialogComponent,
    CardDialogComponent,
    DateFormatPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MatListModule,
    DragDropModule,
    MatButtonModule,
    MatDialogModule,
    MatInputModule,
    MatSelectModule,
  ],
  exports: [CardDialogComponent, DateFormatPipe],
  providers: [{ provide: LOCALE_ID, useValue: 'ru' }],
  bootstrap: [AppComponent],
  entryComponents: [CardDialogComponent, ProjectDialogComponent]
})
export class AppModule {
}
