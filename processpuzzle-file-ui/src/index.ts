import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SampleDirective } from './sample.directive';
import { SamplePipe } from './sample.pipe';
import { SampleService } from './sample.service';
import { FileUploadComponent } from './file-upload.component';
import { HttpClientModule } from '@angular/common/http';

export * from './file-upload.component';
export * from './sample.directive';
export * from './sample.pipe';
export * from './sample.service';

@NgModule({
  imports: [
    CommonModule,
     HttpClientModule
  ],
  declarations: [
    FileUploadComponent,
    SampleDirective,
    SamplePipe
  ],
  exports: [
    FileUploadComponent,
    SampleDirective,
    SamplePipe
  ]
})
export class SampleModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SampleModule,
      providers: [SampleService]
    };
  }
}
